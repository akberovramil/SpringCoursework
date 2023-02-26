package akberov.ramil.coursework.controllers;

import akberov.ramil.coursework.exceptions.NumberOfSocksException;
import akberov.ramil.coursework.exceptions.SocksNotFoundException;
import akberov.ramil.coursework.model.Colors;
import akberov.ramil.coursework.model.Size;
import akberov.ramil.coursework.model.Socks;
import akberov.ramil.coursework.service.FilesService;
import akberov.ramil.coursework.service.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


@RestController
@RequestMapping("/socks")
@Tag(name = "Носки", description = "CRUD-операции и другие эндпоинты для работы со складом носков")
public class SocksController {
    private final SocksService socksService;
    private FilesService filesService;

    public SocksController(SocksService socksService, FilesService filesService) {
        this.socksService = socksService;
        this.filesService = filesService;
    }

    @PostMapping()
    @Operation(
            summary = "Приход товара на склад"
    )
    public ResponseEntity<Object> addSocks(@RequestParam(name = "color") Colors color,
                                           @RequestParam(name = "size") Size size,
                                           @RequestParam(name = "cotton") Integer cotton,
                                           @RequestParam(name = " quantity") Long quantity) {
        try {
            socksService.addSocks(color, size, cotton, quantity);
        } catch (SocksNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();


    }

    @GetMapping()
    @Operation(
            summary = "Возвращает общее количество носков на складе по определенным критериям"
    )
    public ResponseEntity<Object> getSelectedSock(@RequestParam(name = "color") Colors color,
                                                  @RequestParam(name = "size") Size size,
                                                  @RequestParam(name = "cotton") Integer cotton,
                                                  @RequestParam(required = false) Long quantity) {

        try {
            Long amountSocks = socksService.getNumberOfSocks(color, size, cotton);
            return ResponseEntity.ok(amountSocks);
        } catch (SocksNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    @Operation(
            summary = "Отпуск товара с склада"
    )

    public ResponseEntity<Object> saleSocks(@RequestParam(name = "color") Colors color,
                                            @RequestParam(name = "size") Size size,
                                            @RequestParam(name = "cotton") Integer cotton,
                                            @RequestParam(name = " quantity") Long quantity) {
        try {
            socksService.saleSocks(color, size, cotton, quantity);
        } catch (SocksNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NumberOfSocksException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    @Operation(
            summary = "Удаление бракованных носков"
    )
    public ResponseEntity<Object> deleteDefectiveSocks( @RequestParam(name = "color") Colors color,
                                                       @RequestParam(name = "size") Size size,
                                                       @RequestParam(name = "cotton") Integer cotton,
                                                       @RequestParam(name = "quantity") Long quantity) {

        try {
            socksService.deleteDefectiveSocks(color, size, cotton, quantity);
        } catch (SocksNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NumberOfSocksException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();

    }

    @GetMapping("/import")
    @Operation(
            summary = "Скачать список носков"
    )
    public ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException {
        File file = filesService.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"SocksLog.json\"")
                    .body(resource);

        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/export", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузить список носков"
    )
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) {
        filesService.cleanDataFile();
        File dataFile = filesService.getDataFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {

            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @GetMapping("/recipeReport")
    @Operation(
            summary = "Получение отчёта о товаре в формате txt",
            description = "Все носки "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные о товаре получены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            )
    })

    public ResponseEntity<InputStreamResource> getRecipesReport() {
        try {
            Path path = socksService.createReport();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"SocksLog.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

}

