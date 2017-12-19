

from captureAgents import CaptureAgent
from baselineTeam import ReflexCaptureAgent
import distanceCalculator
import random, time, util, sys
from game import Directions
import game
from util import nearestPoint
from game import Actions

DEBUG = False
DEFENSE_TIMER_MAX = 100.0
USE_BELIEF_DISTANCE = True
arguments = {}

MINIMUM_PROBABILITY = .0001
PANIC_TIME = 80
CHEAT = False
beliefs = []
beliefsInitialized = []
FORWARD_LOOKING_LOOPS = 1


def createTeam(firstIndex, secondIndex, isRed,
               first='MyTopAgent', second='MyBottomAgent', **args):
    if 'numTraining' in args:
        arguments['numTraining'] = args['numTraining']
    return [eval(first)(firstIndex), eval(second)(secondIndex)]


class ApproximateQAgent(CaptureAgent):
    def __init__(self, index):
        CaptureAgent.__init__(self, index)
        self.weights = util.Counter()
        self.numTraining = 0
        self.episodesSoFar = 0
        self.epsilon = 0.05
        self.discount = 0.8
        self.alpha = 0.2

    def registerInitialState(self, gameState):
        self.start = gameState.getAgentPosition(self.index)
        self.lastAction = None
        CaptureAgent.registerInitialState(self, gameState)

    def getSuccessor(self, gameState, action):
        successor = gameState.generateSuccessor(self.index, action)
        pos = successor.getAgentState(self.index).getPosition()
        if pos != nearestPoint(pos):
            # Only half a grid position was covered
            return successor.generateSuccessor(self.index, action)
        else:
            return successor

    def chooseAction(self, state):
        # Append game state to observation history...
        self.observationHistory.append(state)
        # Pick Action
        legalActions = state.getLegalActions(self.index)
        action = None
        if (DEBUG):
            print self.newline()
            print "AGENT " + str(self.index) + " choosing action!"
        if len(legalActions):
            if util.flipCoin(self.epsilon) and self.isTraining():
                action = random.choice(legalActions)
                if (DEBUG):
                    print "ACTION CHOSE FROM RANDOM: " + action
            else:
                action = self.computeActionFromQValues(state)
                if (DEBUG):
                    print "ACTION CHOSE FROM Q VALUES: " + action

        self.lastAction = action

        foodLeft = len(self.getFood(state).asList())
        if foodLeft <= 2:
            bestDist = 9999
            for a in legalActions:
                successor = self.getSuccessor(state, a)
                pos2 = successor.getAgentPosition(self.index)
                dist = self.getMazeDistance(self.start, pos2)
                if dist < bestDist:
                    action = a
                    bestDist = dist

        if (DEBUG):
            print "AGENT " + str(self.index) + " chose action " + action + "!"
        return action

    def getFeatures(self, gameState, action):
        successor = self.getSuccessor(gameState, action)
        features = util.Counter()
        features['score'] = self.getScore(successor)
        if not self.red:
            features['score'] *= -1
        features['choices'] = len(successor.getLegalActions(self.index))
        return features

    def computeActionFromQValues(self, state):
        bestValue = -999999
        bestActions = None
        for action in state.getLegalActions(self.index):
            value = self.getQValue(state, action)
            if (DEBUG):
                print "ACTION: " + action + "           QVALUE: " + str(value)
            if value > bestValue:
                bestActions = [action]
                bestValue = value
            elif value == bestValue:
                bestActions.append(action)
        if bestActions == None:
            return Directions.STOP  # If no legal actions return None
        return random.choice(bestActions)  # Else choose one of the best actions randomly

    def getWeights(self):
        return self.weights

    def computeValueFromQValues(self, state):
        bestValue = -999999
        noLegalActions = True
        for action in state.getLegalActions(self.index):
            # For each action, if that action is the best then
            # update bestValue
            noLegalActions = False
            value = self.getQValue(state, action)
            if value > bestValue:
                bestValue = value
        if noLegalActions:
            return 0  # If there is no legal action return 0
        # Otherwise return the best value found
        return bestValue

    def getQValue(self, state, action):
        total = 0
        weights = self.getWeights()
        features = self.getFeatures(state, action)
        for feature in features:
            # Implements the Q calculation
            total += features[feature] * weights[feature]
        return total

    def getReward(self, gameState):
        foodList = self.getFood(gameState).asList()
        return -len(foodList)

    def observationFunction(self, gameState):
        if len(self.observationHistory) > 0 and self.isTraining():
            self.update(self.getCurrentObservation(), self.lastAction, gameState, self.getReward(gameState))
        return gameState.makeObservation(self.index)

    def isTraining(self):
        return self.episodesSoFar < self.numTraining

    def update(self, state, action, nextState, reward):
        """
           Should update your weights based on transition
        """
        if (DEBUG):
            print self.newline()
            print "AGENT " + str(self.index) + " updating weights!"
            print "Q VALUE FOR NEXT STATE: " + str(self.computeValueFromQValues(nextState))
            print "Q VALUE FOR CURRENT STATE: " + str(self.getQValue(state, action))
        difference = (reward + self.discount * self.computeValueFromQValues(nextState))
        difference -= self.getQValue(state, action)
        newWeights = self.weights.copy()
        features = self.getFeatures(state, action)
        for feature in features:
            # Implements the weight updating calculations
            newWeight = newWeights[feature] + self.alpha * difference * features[feature]
            if (DEBUG):
                print "AGENT " + str(self.index) + " weights for " + feature + ": " + str(
                    newWeights[feature]) + " ---> " + str(newWeight)
            newWeights[feature] = newWeight
        self.weights = newWeights.copy()
        # print "WEIGHTS AFTER UPDATE"
        # print self.weights

    def newline(self):
        return "-------------------------------------------------------------------------"

    def final(self, state):
        "Called at the end of each game."
        # call the super-class final method
        CaptureAgent.final(self, state)
        if self.isTraining() and DEBUG:
            print "END WEIGHTS"
            print self.weights
        self.episodesSoFar += 1
        if self.episodesSoFar == self.numTraining:
            print "FINISHED TRAINING"


class MyCaptureAgent(ApproximateQAgent):
    def registerInitialState(self, gameState):
        ApproximateQAgent.registerInitialState(self, gameState)
        self.favoredY = 0.0
        self.defenseTimer = DEFENSE_TIMER_MAX
        self.getLegalPositions(gameState)

    def __init__(self, index):
        ApproximateQAgent.__init__(self, index)
        self.weights = util.Counter()
        self.weights['successorScore'] = 100
        self.weights['MyDistanceToFood'] = -1
        self.weights['ghostDistance'] = 5
        self.weights['stop'] = -1000
        self.weights['legalActions'] = 100
        self.weights['powerPelletValue'] = 100
        self.distanceToTrackPowerPelletValue = 3
        self.weights['backToSafeZone'] = -1
        self.minPelletsToCashIn = 8
        self.weights['chaseEnemyValue'] = -100
        self.chaseEnemyDistance = 5
        self.threatenedDistance = 5
        # dictionary of (position) -> [action, ...]
        # populated as we go along; to use this, call self.getLegalActions(gameState)
        self.legalActionMap = {}
        self.legalPositionsInitialized = False
        if DEBUG:
            print "INITIAL WEIGHTS"
            print self.weights

    def getWinningBy(self, gameState):
        if self.red:
            return gameState.getScore()
        else:
            return -1 * gameState.getScore()

    def getLegalPositions(self, gameState):
        if not self.legalPositionsInitialized:
            self.legalPositions = []
            walls = gameState.getWalls()
            for x in range(walls.width):
                for y in range(walls.height):
                    if not walls[x][y]:
                        self.legalPositions.append((x, y))
            self.legalPositionsInitialized = True
        return self.legalPositions

    def getLegalActions(self, gameState):
        currentPos = gameState.getAgentState(self.index).getPosition()
        if currentPos not in self.legalActionMap:
            self.legalActionMap[currentPos] = gameState.getLegalActions(self.index)
        return self.legalActionMap[currentPos]

    def shouldRunHome(self, gameState):
        return False

    def getFeatures(self, gameState, action):
        self.observeAllOpponents(gameState)
        features = util.Counter()
        successor = self.getSuccessor(gameState, action)
        myState = successor.getAgentState(self.index)
        myPos = myState.getPosition()
        foodList = self.getFood(successor).asList()
        features['successorScore'] = -len(foodList)

        if len(foodList) > 0:  # This should always be True,  but better safe than sorry
            MyDistance = min([self.getMyDistance(myPos, food) for food in foodList])
            features['MyDistanceToFood'] = MyDistance

        # Grab all enemies
        enemies = [successor.getAgentState(i) for i in self.getOpponents(successor)]
        enemyPacmen = [a for a in enemies if a.isPacman and a.getPosition() != None]
        nonScaredGhosts = [a for a in enemies if not a.isPacman and a.getPosition() != None and not a.scaredTimer > 0]
        scaredGhosts = [a for a in enemies if not a.isPacman and a.getPosition() != None and a.scaredTimer > 0]

        dists = []
        for index in self.getOpponents(successor):
            enemy = successor.getAgentState(index)
            if enemy in nonScaredGhosts:
                if USE_BELIEF_DISTANCE:
                    dists.append(self.getMazeDistance(myPos, self.getMostLikelyGhostPosition(index)))
                else:
                    dists.append(self.getMazeDistance(myPos, enemy.getPosition()))
        # Use the smallest distance
        if len(dists) > 0:
            smallestDist = min(dists)
            features['ghostDistance'] = smallestDist

        features['powerPelletValue'] = self.getPowerPelletValue(myPos, successor, scaredGhosts)
        features['chaseEnemyValue'] = self.getChaseEnemyWeight(myPos, enemyPacmen)

        # If on defense, heavily value chasing after enemies
        if self.defenseTimer > 0:
            self.defenseTimer -= 1
            features['chaseEnemyValue'] *= 100

        if len(self.getFoodYouAreDefending(successor).asList()) <= 2:
            features['chaseEnemyValue'] *= 100

        # Heavily prioritize not stopping
        if action == Directions.STOP:
            features['stop'] = 1

        features['legalActions'] = self.getLegalActionModifier(gameState, FORWARD_LOOKING_LOOPS)

        features['backToSafeZone'] = self.getCashInValue(myPos, gameState, myState)

        features['backToSafeZone'] += self.getBackToStartDistance(myPos, features['ghostDistance'])

        if self.shouldRunHome(gameState):
            features['backToSafeZone'] = self.getMazeDistance(self.start, myPos) * 10000

        return features

    def getWeights(self):
        return self.weights

    def getMyDistance(self, myPos, food):
        return self.getMazeDistance(myPos, food) + abs(self.favoredY - food[1])

    def getPowerPelletValue(self, myPos, successor, scaredGhosts):
        powerPellets = self.getCapsules(successor)
        minDistance = 0
        if len(powerPellets) > 0 and len(scaredGhosts) == 0:
            distances = [self.getMazeDistance(myPos, pellet) for pellet in powerPellets]
            minDistance = min(distances)
        return max(self.distanceToTrackPowerPelletValue - minDistance, 0)

    def getCashInValue(self, myPos, gameState, myState):
        return 0

    def getBackToStartDistance(self, myPos, smallestGhostPosition):
        if smallestGhostPosition > self.threatenedDistance or smallestGhostPosition == 0:
            return 0
        else:
            return self.getMazeDistance(self.start, myPos) * 1000

    def getChaseEnemyWeight(self, myPos, enemyPacmen):
        if len(enemyPacmen) > 0:
            dists = [self.getMazeDistance(myPos, enemy.getPosition()) for enemy in enemyPacmen]
            # Use the smallest distance
            if len(dists) > 0:
                smallestDist = min(dists)
                return smallestDist
        return 0

    def getMostLikelyGhostPosition(self, ghostAgentIndex):
        return max(beliefs[ghostAgentIndex])

    def getLegalActionModifier(self, gameState, numLoops):
        legalActions = self.getLegalActions(gameState)
        numActions = len(legalActions)
        for legalAction in legalActions:
            if numLoops > 0:
                newState = self.getSuccessor(gameState, legalAction)
                numActions += self.getLegalActionModifier(newState, numLoops - 1)
        return numActions


    def initializeBeliefs(self, gameState):
        beliefs.extend([None for x in range(len(self.getOpponents(gameState)) + len(self.getTeam(gameState)))])
        for opponent in self.getOpponents(gameState):
            self.initializeBelief(opponent, gameState)
        beliefsInitialized.append('done')

    def initializeBelief(self, opponentIndex, gameState):
        belief = util.Counter()
        for p in self.getLegalPositions(gameState):
            belief[p] = 1.0
        belief.normalize()
        beliefs[opponentIndex] = belief

    def observeAllOpponents(self, gameState):
        if len(beliefsInitialized):
            for opponent in self.getOpponents(gameState):
                self.observeOneOpponent(gameState, opponent)
        else:
            self.initializeBeliefs(gameState)

    def observeOneOpponent(self, gameState, opponentIndex):
        pacmanPosition = gameState.getAgentPosition(self.index)
        allPossible = util.Counter()
        maybeDefinitePosition = gameState.getAgentPosition(opponentIndex)
        if maybeDefinitePosition != None:
            allPossible[maybeDefinitePosition] = 1
            beliefs[opponentIndex] = allPossible
            return
        noisyDistance = gameState.getAgentDistances()[opponentIndex]
        for p in self.getLegalPositions(gameState):
            trueDistance = util.manhattanDistance(p, pacmanPosition)
            modelProb = gameState.getDistanceProb(trueDistance,
                                                  noisyDistance)  # Find the probability of getting this noisyDistance if the ghost is at this position
            if modelProb > 0:
                oldProb = beliefs[opponentIndex][p]
                allPossible[p] = (oldProb + MINIMUM_PROBABILITY) * modelProb
            else:
                allPossible[p] = 0
        allPossible.normalize()
        beliefs[opponentIndex] = allPossible

    def observationFunction(self, gameState):
        if CHEAT:
            return gameState
        else:
            return ApproximateQAgent.observationFunction(self, gameState)


class MyTopAgent(MyCaptureAgent):
    def registerInitialState(self, gameState):
        MyCaptureAgent.registerInitialState(self, gameState)
        self.favoredY = gameState.data.layout.height


class MyBottomAgent(MyCaptureAgent):
    def registerInitialState(self, gameState):
        MyCaptureAgent.registerInitialState(self, gameState)
        self.favoredY = 0.0