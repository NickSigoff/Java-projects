package com.shpp.p2p.cs.nsigov.assignment14;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * The class unpacks the data packed by the DataCompressor class.
 * The class knows how to fill the byte array received at the input.
 * Allocates service information in the constructor and defines byte
 * arrays with which it will work. Uses two methods for unpacking: for
 * sequences less than 8 bits and equal to 8 bits.
 */
public class DataExtractor {
    /**
     * Multiplier for buffer
     */
    private final int MULTIPLIER_FOR_BUFFER = 10000;

    /**
     * Input file name
     */
    private final String INPUT_FILE_NAME;

    /**
     * Output file name
     */
    private final String OUTPUT_FILE_NAME;

    /**
     * Number of bits in a codeword
     */
    private int bitsInNumber;

    /**
     * A table of unique bytes and their encoded values
     */
    private final Map<Byte, Byte> TABLE_MAP = new HashMap<>();

    /**
     * The size of the data array allocated from the received array
     */
    private long sizeData;

    /**
     * The class constructor defines the fields of the class
     */
    DataExtractor(String input_file_name, String output_file_name) {
        this.INPUT_FILE_NAME = input_file_name;
        this.OUTPUT_FILE_NAME = output_file_name;
    }

    /**
     * The class works with streams of input / output information.
     * And displays service information to the console
     * The buffer size is selected as a multiple of bitsInNumber.
     * The method has a check for the number of bits in the code number,
     * if it is 8, a short unpacking method is performed
     */
    void unarchive() {
        int outputSize = 0;
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(OUTPUT_FILE_NAME));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(INPUT_FILE_NAME));

            int inputSize = bufferedInputStream.available();
            determineServiceData(bufferedInputStream);
            byte[] buffer;
            while ((buffer = bufferedInputStream.readNBytes(bitsInNumber * MULTIPLIER_FOR_BUFFER)).length != 0) {
                byte[] unarchivedData = bitsInNumber == Byte.SIZE ? unarchiveData8Bits(buffer) : unarchiveData(buffer);
                outputSize += unarchivedData.length;
                bufferedOutputStream.write(unarchivedData);
            }
            bufferedOutputStream.close();
            bufferedInputStream.close();

            System.out.println("Input file size : " + inputSize + " Byte");
            System.out.println("Output file size : " + outputSize + " Byte");
            System.out.println("Unpacking efficiency : " + ((1 - (double) inputSize / (double) outputSize) * 100) + " %");
            System.out.println("Unpacking error : = " + (sizeData - outputSize) + " byte");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * The method unpacks the data according to the table.
     * Uses bit shifts and masks to hide unnecessary bits.
     */
    private byte[] unarchiveData(byte[] buffer) {
        byte[] result = new byte[(int) sizeData < bitsInNumber * MULTIPLIER_FOR_BUFFER ?
                (int) sizeData : (int) Math.floor((double) buffer.length * Byte.SIZE / bitsInNumber)];

        int numberProcessedBits = 0;  // Number of processed bits in the current byte
        byte mainMask = (byte) (-Math.pow(2, Byte.SIZE - bitsInNumber));  //Mask to hide unnecessary bits in the current byte
        int index = 0;  // An iterator over the output array
        byte keyMap;

        for (int i = 0; i < buffer.length; i++) {
            byte currentByte = (byte) (buffer[i] << numberProcessedBits);

            while ((numberProcessedBits + bitsInNumber <= Byte.SIZE) && (i < buffer.length - 1)) {
                keyMap = (byte) ((currentByte & mainMask & 0xff) >>> (Byte.SIZE - bitsInNumber));
                result[index++] = TABLE_MAP.get(keyMap);
                numberProcessedBits += bitsInNumber;
                currentByte = (byte) (currentByte << bitsInNumber);
            }
            if ((numberProcessedBits != Byte.SIZE) && (i < buffer.length - 1)) {
                numberProcessedBits = processRestBits(numberProcessedBits, buffer, i, currentByte, result, index);
                index++;
                continue;
            }
            numberProcessedBits = 0;

        }
        return result;
    }

    /**
     * This method is called when the required key is in different bytes
     *
     * @param numberProcessedBits number of processed bits in the current byte
     * @param buffer              data array
     * @param i                   index of the current byte read from buffer
     * @param currentByte         the current byte being read from the buffer
     * @param result              output unarchived array
     * @param index               index of an empty cell to write to the output array
     * @return number of processed bits in the next byte
     */
    private int processRestBits(int numberProcessedBits, byte[] buffer, int i, byte currentByte, byte[] result, int index) {
        // Number of unprocessed bits in the current byte
        int remainingBits = Byte.SIZE - numberProcessedBits;
        // The next byte of the array relative to the current
        byte nextByte;
        // The remaining bits in the current byte after shift
        byte remainingByte;

        // If the bits from the next byte are needed, the mask covers the unnecessary bits.
        nextByte = (byte) ((buffer[i + 1]) & ((byte) -Math.pow(2, Byte.SIZE - (bitsInNumber - remainingBits))));
        nextByte >>>= (Byte.SIZE - (bitsInNumber - remainingBits));
        // Mask hiding unnecessary bits after shifting
        nextByte &= (byte) (Math.pow(2, (bitsInNumber - remainingBits)) - 1);

        remainingByte = (byte) ((currentByte & 0xff) >>> numberProcessedBits - (bitsInNumber - remainingBits));
        remainingByte |= nextByte;
        result[index] = TABLE_MAP.get(remainingByte);

        return bitsInNumber - remainingBits;
    }

    /**
     * The method processes technical information from a packed file. By known bytes, the method fills its variables
     *
     * @param bufferedInputStream class object bufferedInputStream
     */
    private void determineServiceData(BufferedInputStream bufferedInputStream) {
        try {
            // 0 - 4 byte size of the Table
            byte[] sizeTableArray = bufferedInputStream.readNBytes(4);
            ByteBuffer wrapped = ByteBuffer.wrap(sizeTableArray);
            int sizeTable = wrapped.getInt();
            // 4 - 12 8 byte size of data before archiving + table
            byte[] serviceDataArray = bufferedInputStream.readNBytes(8 + sizeTable);
            // byte size of data before archiving
            byte[] dataArray = Arrays.copyOfRange(serviceDataArray, 0, 8);
            wrapped = ByteBuffer.wrap(dataArray);
            sizeData = wrapped.getLong();

            byte[] table = Arrays.copyOfRange(serviceDataArray, 8, 8 + sizeTable);
            for (int i = 0; i < table.length - 1; i = i + 2) {
                TABLE_MAP.put(table[i + 1], table[i]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        bitsInNumber = bitsInNumber(TABLE_MAP.size());
    }

    /**
     * The method unzips data is used when the number of bits by which the bytes were encoded earlier than 8
     *
     * @param inputArray an array of bytes coming to the input
     * @return unzips data
     */
    private byte[] unarchiveData8Bits(byte[] inputArray) {
        byte[] unzipsData = new byte[inputArray.length];
        int i = 0;
        for (byte b : inputArray) {
            unzipsData[i++] = TABLE_MAP.get(b);
        }
        return unzipsData;
    }

    /**
     * The method determines how many bits are needed to encode a number
     *
     * @param number The number to encode
     * @return Number of bits required for encoding
     */
    private int bitsInNumber(int number) {
        int result = 0;
        while (number > 0) {
            number >>= 1;
            result++;
        }
        return Math.min(result, 8);
    }
}