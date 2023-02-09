package akberov.ramil.coursework.service;

import akberov.ramil.coursework.model.Colors;
import akberov.ramil.coursework.model.Size;
import akberov.ramil.coursework.model.Socks;

import java.io.IOException;
import java.nio.file.Path;

public interface SocksService {



    void addSocks(Colors color, Size size, Integer cotton, Long quantity);

    void saleSocks(Colors color, Size size, Integer cotton, Long soldQuantit) ;

    Long getNumberOfSocks(Colors color, Size size, Integer cotton);


    void deleteDefectiveSocks(Colors color, Size size, Integer cotton, Long quantity);

    Path createReport() throws IOException;



}
