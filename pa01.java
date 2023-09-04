/*=============================================================================
| Assignment: pa01 - Encrypting a plaintext file using the Vigenere cipher
|
| Author: Kirk Lefevre
| Language: c, c++, Java, go, python
|
| To Compile: javac pa01.java
| gcc -o pa01 pa01.c
| g++ -o pa01 pa01.cpp
| go build pa01.go
|
| To Execute: java -> java pa01 kX.txt pX.txt
| or c++ -> ./pa01 kX.txt pX.txt
| or c -> ./pa01 kX.txt pX.txt
| or go -> ./pa01 kX.txt pX.txt
| or python -> python3 pa01.py kX.txt pX.txt
| where kX.txt is the keytext file
| and pX.txt is plaintext file
|
| Note: All input files are simple 8 bit ASCII input
|
| Class: CIS3360 - Security in Computing - Spring 2023
| Instructor: McAlpin
| Due Date: 3/6/23
|
+=============================================================================*/

//I copied the above note exactly, and I only changed my name and due date.
//It says Vigenere cipher, but the assignment is meant to be the Hill Cipher

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class pa01 {
    static int size=0;//size of the key array
    public static void main(String args[]) throws FileNotFoundException{
        int[][] key = intopen(args[0]);//open and read the key array
        char[] arr = chopen(args[1]);//open and read the plaintext file
        char[] tmp = new char[size];//temporary encryption blocks
        String cipharr = "";//empty encryption/ciphertext string

        for(int i=0; i<arr.length; i++){//encrypt the data block by block
            for(int j=0; j<size; j++){
                tmp[j] = arr[j+i];
            }
            cipharr += bloccrypt(key, tmp);
            i+=size-1;
        }

        printAll(key, arr, cipharr.toCharArray());//print everything
    }

    static int[][] tomatrix(char[] arr){//make the matrix equivalent of the given string
        int[][] matrix = new int[size][1];
        for(int i=0; i<size; i++){
            matrix[i][0] = arr[i] - 'a';//subtract a to get the chars place in the alphabet
        }

        return matrix;
    }
    
    static String bloccrypt(int[][]key, char[] plain){//main encryption function
        String cipher = new String();//cipher string
        int[][] cm = new int[size][size];//cipher matrix
        int[][] pm = tomatrix(plain);//plaintext matrix
        for(int i=0; i<size; i++){//the professor's given encryption/matrix muliplication logic
            for(int j=0; j<1; j++){
                cm[i][j] = 0;
                for(int k=0; k<size; k++){
                    cm[i][j] += key[i][k] * pm[k][j];
                }
            }
            cipher+=(char)(cm[i][0]%26 + 'a');//fill the cipher string
        }

        return cipher;
    }

    static boolean isLetter(char ch){//checks if its a letter.
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }

    static int[][] intopen(String str) throws FileNotFoundException{//opens the key file and reads it
        File file = new File(str);
        Scanner scan = new Scanner(file);
        size = scan.nextInt();
        int[][] arr = new int[size][size];

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                arr[i][j] = scan.nextInt();
            }
        }

        scan.close();

        return arr;
    }

    static char[] chopen(String str)throws FileNotFoundException{//opens the plaintext file and reads it
        File file = new File(str);
        Scanner scan = new Scanner(file);
        String temp = new String();

        while(scan.hasNext()){
            temp += scan.next();
        }

        scan.close();

        return cull(temp.toLowerCase().toCharArray(), temp.length());//returns the filtered file (thats lowercase and only letters)
    }

    static char[] cull(char[] arr, int len){//removes spaces/special characters and numbers
        String arr2 = new String();
        for(int i=0; i<len; i++){
            if(isLetter(arr[i])){
                arr2+=arr[i];
            }
        }
        
        return paddington(arr2);//returns a padded thingy if necessary
    }

    static char[] paddington(String arr){//pads the stringy thing if necessary
        int x=arr.length();
        while(x%size!=0){
            x++;
            arr+='x';
        }

        return arr.toCharArray();
    }

    static void printAll(int[][] key, char[] plaintext, char[] ciphertext){//prints all
        System.out.println("\nKey matrix:");

        for(int i=0; i<size; i++){//print the key matrix with exact spacing and newlines
            for(int j=0; j<size; j++){
                if(key[i][j]<10)
                    System.out.print("   " + key[i][j]);
                else
                    System.out.print("  " + key[i][j]);
            }
            System.out.println();
        }

        System.out.println("\nPlaintext:");

        for(int i=0; i<plaintext.length; i++){//print the plaintext with a new line every 80 characters
            if(i%80==0 && i>0)
                System.out.println();
            System.out.print(plaintext[i]);
        }
        System.out.println();

        System.out.println("\nCiphertext:");

        for(int i=0; i<ciphertext.length; i++){//print the ciphertext with a newline every 80 characters
            if(i%80==0 && i>0)
                System.out.print("\n");
            System.out.print(ciphertext[i]);
        }
        System.out.println();
    }

}

/*=============================================================================
| I Kirk Lefevre (ki689220) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/