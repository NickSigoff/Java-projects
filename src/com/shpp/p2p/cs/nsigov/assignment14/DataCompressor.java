package com.shpp.p2p.cs.nsigov.assignment14;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * Data compression class.
 * Receives as an input an array of bytes received from ArchiveManager
 * and on the basis of these data builds a table of code values
 * for unique bytes of information. Uses two methods for archiving data.
 * The main method works with code sequences from 1 to 7 bits, the second only for 8 bits,
 * since the archiving process will be much easier. Forms an output byte array which contains:
 * bytes 1 - 4 size of coded character table
 * bytes 5 - 12 size of data received at the input of the class
 * The next bytes are the encoded table and data
 */
public class DataCompressor {
    /**
     * Multiplier for buffer
     */
    private final int MULTIPLIER_FOR_BUFFER = 10000;

    /**
     * Number of bits in a codeword
     */
    private int bitsInNumber;

    /**
     * A table of unique bytes and their encoded values
     */
    private Map<Byte, Byte> table;

    /**
     * Input file size
     */
    private long inputFileSize;

    /**
     * Input file name
     */
    private final String INPUT_FILE_NAME;

    /**
     * Output file name
     */
    private final String OUTPUT_FILE_NAME;

    /**
     * Class constructor defines class fields INPUT_FILE_NAME, OUTPUT_FILE_NAME
     *
     * @param input_file_name  Input file name
     * @param output_file_name Output file name
     */
    DataCompressor(String input_file_name, String output_file_name) {
        this.INPUT_FILE_NAME = input_file_name;
        this.OUTPUT_FILE_NAME = output_file_name;
    }

    /**
     * The method archives data. Uses a buffer to read and write data piece by piece.
     * The buffer size is selected as a multiple of bitsInNumber.
     * Displays service information to the console
     */
    void archive() {
        int outputFileSize = 0;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_FILE_NAME);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            FileInputStream fileInputStream = new FileInputStream(INPUT_FILE_NAME);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            inputFileSize = fileInputStream.available();
            byte[] serviceData = definitionServiceInformation();
            outputFileSize += serviceData.length;
            bufferedOutputStream.write(serviceData);
            byte[] buffer;

            while ((buffer = bufferedInputStream.readNBytes(bitsInNumber * MULTIPLIER_FOR_BUFFER)).length != 0) {
                byte[] archivedData = bitsInNumber == 8 ? archiveData8Bits(table, buffer) : archiveData(table, buffer);
                outputFileSize += archivedData.length;
                bufferedOutputStream.write(archivedData);
            }
            bufferedOutputStream.close();
            bufferedInputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        System.out.println("Input file size : " + inputFileSize + " Byte");
        System.out.println("Output file size : " + outputFileSize + " Byte");
        System.out.println("Archiving efficiency : " + ((1 - (double) outputFileSize / (double) inputFileSize) * 100) + " %");
    }

    /**
     * The method defines and creates an array of service information:
     * bytes 1 - 4 size of coded character table
     * bytes 5 - 12 size of data received at the input of the class
     * bytes X bytes of the Table
     *
     * @return Service data array
     */
    private byte[] definitionServiceInformation() {
        table = madeTable();
        byte[] tableSize = ByteBuffer.allocate(4).putInt(table.size() * 2).array(); // 4 - size of int
        byte[] dataSize = ByteBuffer.allocate(8).putLong(inputFileSize).array(); // 8 - size of long
        byte[] tableArray = archiveTable();
        byte[] serviceData = new byte[tableSize.length + dataSize.length + tableArray.length];

        System.arraycopy(tableSize, 0, serviceData, 0, tableSize.length);
        System.arraycopy(dataSize, 0, serviceData, tableSize.length, dataSize.length);
        System.arraycopy(tableArray, 0, serviceData, tableSize.length + dataSize.length, tableArray.length);
        return serviceData;
    }

    /**
     * Data archiving method when using 8 bits
     *
     * @param table          HashMap value unique byte - its code
     * @param unarchivedData Input data array
     * @return archived data
     */
    private byte[] archiveData8Bits(Map<Byte, Byte> table, byte[] unarchivedData) {
        byte[] result = new byte[unarchivedData.length];
        int i = 0;
        for (byte b : unarchivedData) {
            result[i] = table.get(b);
            i++;
        }
        return result;
    }

    /**
     * The method archives the table of values. Writes information to whole bytes
     *
     * @return archived table
     */
    private byte[] archiveTable() {
        byte[] archivedTable = new byte[table.size() * 2];
        int i = 0;
        for (Map.Entry<Byte, Byte> entry : table.entrySet()) {
            archivedTable[i] = entry.getKey();
            i++;
            archivedTable[i] = entry.getValue();
            i++;
        }
        return archivedTable;
    }

    /**
     * The method archives the data received as input.
     * The loop goes through the entire input data array
     * and each byte is assigned its own code in accordance
     * with the table. Short bytes are packed into a new array.
     * Uses bit shifts to fill the elements with the output data array.
     * Uses bit shifts to correctly position the code sequences
     * and mask to hide unnecessary parts of the byte
     *
     * @param table          HashMap value unique byte - its code
     * @param unarchivedData Input data array
     * @return archived data
     */
    private byte[] archiveData(Map<Byte, Byte> table, byte[] unarchivedData) {
        byte[] zipData = new byte[(int) Math.ceil((double) unarchivedData.length * bitsInNumber / Byte.SIZE)];
        // an iterator to an zipData array
        int i = 0;
        // number of processed bits per byte
        int numberFilledBits = 0;
        // The main mask that covers unnecessary bits of information. Used to separate the desired number of bits
        byte mainMask = (byte) (Math.pow(2, bitsInNumber) - 1);
        //Masks split a byte into two parts for writing to different bytes
        byte leftMask;
        byte rightMask;
        for (byte b : unarchivedData) {
            byte currentByte = table.get(b); // get a meaning from the table

            if ((numberFilledBits + bitsInNumber < Byte.SIZE) && numberFilledBits == 0) {
                zipData[i] = (byte) (zipData[i] | currentByte & mainMask);
                numberFilledBits += bitsInNumber;

            } else if ((numberFilledBits + bitsInNumber < Byte.SIZE)) {
                zipData[i] <<= bitsInNumber;
                zipData[i] = (byte) (zipData[i] | currentByte & mainMask);
                numberFilledBits += bitsInNumber;

            } else if (numberFilledBits + bitsInNumber == Byte.SIZE) {
                zipData[i] <<= bitsInNumber;
                zipData[i] = (byte) (zipData[i] | currentByte & mainMask);
                i++;
                numberFilledBits = 0;

            } else { // need to divide the bits into parts
                zipData[i] <<= Byte.SIZE - numberFilledBits;
                leftMask = (byte) (Math.pow(2, bitsInNumber) - Math.pow(2, bitsInNumber - (Byte.SIZE - numberFilledBits)));
                rightMask = (byte) (Math.pow(2, bitsInNumber - (Byte.SIZE - numberFilledBits)) - 1);
                zipData[i] = (byte) (zipData[i] | ((currentByte & leftMask) >> (bitsInNumber - (Byte.SIZE - numberFilledBits))));
                i++;
                zipData[i] = (byte) (zipData[i] | currentByte & rightMask);
                numberFilledBits = bitsInNumber - (Byte.SIZE - numberFilledBits);
            }
        }
        // for last byte
        if (numberFilledBits != Byte.SIZE && numberFilledBits != 0 && zipData.length > 1) {
            zipData[zipData.length - 1] <<= (Byte.SIZE - numberFilledBits);
        }

        return zipData;
    }

    /**
     * The method creates a table of correspondence between the encoded byte and its value
     * A total of 256 unique byte combinations are possible
     *
     * @return HashMap filled with unique bytes with their encoded values
     */
    private Map<Byte, Byte> madeTable() {
        Map<Byte, Byte> uniqueByteTable = new HashMap<>();
        Set<Byte> uniqueByteSet = madeCollection();
        bitsInNumber = bitsInNumber(uniqueByteSet.size());
        byte i = 0;// minimum byte value
        for (Byte uniqueByte : uniqueByteSet) {
            uniqueByteTable.put(uniqueByte, i++);
        }
        return uniqueByteTable;
    }

    /**
     * The method creates a collection of unique characters encountered in the input byte array
     * Does a full walk through the array to find all values
     * Uses a bufferedInputStream to read the file piece by piece
     *
     * @return set unique bits
     */
    private Set<Byte> madeCollection() {
        Set<Byte> uniqueSymbols = new HashSet<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(INPUT_FILE_NAME);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            byte[] buffer;
            while ((buffer = bufferedInputStream.readNBytes(64000)).length != 0) {
                for (byte b : buffer) {
                    uniqueSymbols.add(b);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return uniqueSymbols;
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
