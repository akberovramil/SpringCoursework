package akberov.ramil.coursework.service;

import akberov.ramil.coursework.model.Colors;
import akberov.ramil.coursework.model.Size;
import akberov.ramil.coursework.model.Socks;

import java.io.IOException;
import java.nio.file.Path;

public interface SocksService {

    public long addSocks(Socks sock);

    Socks saleSocks(Colors color, Size size, Integer cotton) ;

    Integer getNumberOfSocks(Colors color, Size size, Integer cotton);

    Socks deleteDefectiveSocks(Colors color, Size size, Integer cotton);

    Path createReport() throws IOException;



}
