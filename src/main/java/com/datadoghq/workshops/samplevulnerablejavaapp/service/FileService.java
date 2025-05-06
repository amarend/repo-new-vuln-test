package com.datadoghq.workshops.samplevulnerablejavaapp.service;

import com.datadoghq.workshops.samplevulnerablejavaapp.exception.FileForbiddenFileException;
import com.datadoghq.workshops.samplevulnerablejavaapp.exception.FileReadException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private static final Path ALLOWED_DIRECTORY = Paths.get("/tmp/files").toAbsolutePath().normalize();

    public String readFile(String inputPath) throws FileForbiddenFileException, FileReadException {
        try {
            Path requestedPath = Paths.get(inputPath).toAbsolutePath().normalize();

            if (!requestedPath.startsWith(ALLOWED_DIRECTORY)) {
                throw new FileForbiddenFileException("Access denied: " + inputPath);
            }

            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = Files.newBufferedReader(requestedPath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append(System.lineSeparator());
                }
            }

            return sb.toString();
        } catch (IOException e) {
            throw new FileReadException("Error reading file: " + inputPath, e);
        }
    }
}





/*
package com.datadoghq.workshops.samplevulnerablejavaapp.service;

import com.datadoghq.workshops.samplevulnerablejavaapp.exception.FileForbiddenFileException;
import com.datadoghq.workshops.samplevulnerablejavaapp.exception.FileReadException;
import org.springframework.stereotype.Service;

import java.io.*;




@Service
public class FileService {
    final static String ALLOWED_PREFIX = "/tmp/files/";

    public String readFile(String path) throws FileForbiddenFileException, FileReadException {
        if(!path.startsWith(ALLOWED_PREFIX)) {
            throw new FileForbiddenFileException("You are not allowed to read " + path);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            throw new FileReadException(e.getMessage());
        }
    }
}
*/
