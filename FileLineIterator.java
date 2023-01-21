package org.cis1200.checkers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

    /**
     * FileLineIterator provides a useful wrapper around Java's provided
     * BufferedReader
     */
    public class FileLineIterator implements Iterator<String> {

        private final BufferedReader r;
        private String nextLine;

        /**
         * Creates a FileLineIterator for the reader
         * <p>
         * If an IOException is thrown by the BufferedReader, then hasNext should
         * return false.
         * <p>
         *
         * @param reader - A reader to be turned to an Iterator
         * @throws IllegalArgumentException if reader is null
         */
        public FileLineIterator(BufferedReader reader) {

            // Throw IllegalArgumentException if reader is null
            if (reader == null) {
                throw new IllegalArgumentException();
            }
            this.r = reader;
            try {
                this.nextLine = r.readLine();
            } catch (IOException e) {
                // hasNext should return false
                this.nextLine = null;
            }

        }

        /**
         * Creates a FileLineIterator from a provided filePath by creating a
         * FileReader and BufferedReader for the file.
         * <p>
         *
         *
         * @param filePath - a string representing the file
         * @throws IllegalArgumentException if filePath is null or if the file
         *                                  doesn't exist
         */
        public FileLineIterator(String filePath) {
            this(fileToReader(filePath));
        }

        /**
         * Takes in a filename and creates a BufferedReader.
         * See Java's documentation for BufferedReader to learn how to construct one
         * given a path to a file.
         *
         * @param filePath - the path to the CSV file to be turned to a
         *                 BufferedReader
         * @return a BufferedReader of the provided file contents
         * @throws IllegalArgumentException if filePath is null or if the file
         *                                  doesn't exist
         */
        public static BufferedReader fileToReader(String filePath) {
            BufferedReader in = null;
            if (filePath == null) {
                throw new IllegalArgumentException();
            }

            try {
                in = new BufferedReader(new FileReader(filePath));
            } catch (FileNotFoundException e) {
                throw new IllegalArgumentException();
            }
            return in;
        }

        /**
         * Returns true if there are lines left to read in the file, and false
         * otherwise.
         * <p>
         * If there are no more lines left, this method should close the
         * BufferedReader.
         *
         * @return a boolean indicating whether the FileLineIterator can produce
         *         another line from the file
         */
        @Override
        public boolean hasNext() {
            try {
                //String nextLine = r.readLine();
                if (this.nextLine != null) {
                    return true;
                } else {
                    // close the BufferedReader
                    this.r.close();
                    return false;
                }
            } catch (IOException e) {
                return false;
            }
        }

        /**
         * Returns the next line from the file, or throws a NoSuchElementException
         * if there are no more strings left to return (i.e. hasNext() is false).
         * <p>
         * This method also advances the iterator in preparation for another
         * invocation. If an IOException is thrown during a next() call, your
         * iterator should make note of this such that future calls of hasNext()
         * will return false and future calls of next() will throw a
         * NoSuchElementException
         *
         * @return the next line in the file
         * @throws java.util.NoSuchElementException if there is no more data in the
         *                                          file
         */
        @Override
        public String next() {
            String result = "";

            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            try {
                result = this.nextLine;
                // advance iterator in prep for another invocation
                this.nextLine = r.readLine();
            } catch (IOException e) {
                this.nextLine = null;
            }
            return result;
        }
    }
