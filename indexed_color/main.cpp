#include <iostream>
#include <fstream>

using namespace std;

int main() {
    string filenameS;
    int height, width;
    long lSize;
    size_t result;
    FILE * infile;
    
    /*cout << "What is the height?\n";
    cin >> height;
    cout << "What is the width?\n";
    cin >> width;*/
    height = 40;
    width = 30;
    
    infile = fopen ( "/home/burg/CLionProjects/ReadImage/DemonDeacon30x40.raw" , "rb" );
    if (infile==NULL) {fputs ("File error",stderr); exit (1);}

    // obtain file size:
    fseek(infile , 0 , SEEK_END);
    lSize = ftell (infile);
    rewind (infile);

    // allocate memory to contain the whole file:
    char * image = (char*) malloc (sizeof(unsigned char)*lSize);
    if (image == NULL) {fputs ("Memory error",stderr); exit (2);}

    // copy the file into the image
    result = fread (image,1,lSize,infile);
    if (result != lSize) {fputs ("Reading error",stderr); exit (3);}

    fclose(infile);
    int pix;
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width*3; j= j + 3) {
            cout << "i = " << i << ", j = " << j << endl;
            pix = (int) (*(image + (width * 3)*i + j) * 0.3);
            *(image + (width * 3)*i + j) = pix;
            pix = (int) (*(image + (width * 3)*i + j+1) * 0.3);
            *(image + (width * 3)*i + j+1) = pix;
            pix = (int) (*(image + (width * 3)*i + j)+2* 0.3);
            *(image + (width * 3)*i + j+2) = pix;
        }
    }
    ofstream outfile("/home/burg/CLionProjects/ReadImage/DDLight.raw", ios::out | ios::binary);
    outfile.write((char *) image, height*width*3);
    outfile.close();

}
