# File Zipper Application

## Overview

The **File Zipper** application is a Java Swing-based tool for compressing multiple files and directories into a single ZIP archive. It provides a user-friendly graphical interface for managing files and creating ZIP archives.

## Understanding ZIP Files

ZIP files are a type of archive file format that supports lossless data compression. The ZIP format allows multiple files and directories to be combined into a single file, which is then compressed to reduce its size. This makes it easier to store and transfer multiple files as a single entity.

### Key Concepts Used in Zipping

1. **Compression**: ZIP files use various compression algorithms to reduce the size of the files they contain. The most common algorithm is Deflate, which compresses data by finding and eliminating redundancies.

2. **Archiving**: Archiving involves combining multiple files and directories into one ZIP file. This helps in organizing and managing files more efficiently.

3. **File Entries**: Each file or directory in a ZIP archive is represented as a separate entry. Each entry includes metadata such as the file name, size, and compression method used.

4. **Decompression**: When extracting files from a ZIP archive, the compression is reversed to restore the files to their original state.


## Features

- **Add Files/Directories**: Add files and directories to the list for compression.
- **Delete Files**: Remove selected files from the list.
- **Create ZIP Archive**: Compress selected files and directories into a ZIP file.
- **View History**: Track actions such as added or deleted files with timestamps.

## System Requirements

- **Java Version**: Java 8 or later
- **Operating System**: Windows, macOS, or Linux
- **RAM**: Minimum 512 MB (1 GB recommended)
- **Disk Space**: Sufficient space for file operations

## How to Use

1. **Run the Application**:
   - Ensure you have Java installed.
   - Compile and run the application using your preferred Java IDE or via command line:

     ```bash
     javac FILEZIPPER_PRAC.java
     java FILEZIPPER_PRAC
     ```

2. **Add Files/Directories**:
   - Go to `File > Add` or press `Ctrl + D`.
   - Select files or directories from your file system and click "Open."

3. **Delete Files**:
   - Select files from the list.
   - Go to `File > Delete` or press `Ctrl + U`.

4. **Create ZIP Archive**:
   - Go to `File > Zip` or press `Ctrl + Z`.
   - Choose the location and name for the ZIP file and click "Save."

5. **View History**:
   - Go to `Settings > History` to see a log of actions performed.

## Troubleshooting

- **Issue**: Application fails to start.
  - **Solution**: Ensure Java is correctly installed and added to your system's PATH.

- **Issue**: Error during zipping process.
  - **Solution**: Check file permissions and ensure no files are in use.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For support or inquiries, please contact: [ptlshailyanil@gmail.com](mailto:support@example.com)

## Note

Please be aware that the graphical user interface (GUI) of this application may not be fully optimized or efficient. Development is ongoing, and improvements are planned to enhance performance and usability. Your feedback is welcome as we continue to refine the application.

