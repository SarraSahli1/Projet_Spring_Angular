package com.example.springbootesprit.controller;
import com.example.springbootesprit.entities.Bloc;
import com.example.springbootesprit.entities.Chambre;
import com.example.springbootesprit.entities.TypeChambre;
import com.example.springbootesprit.service.ChambreServiceImp;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Chambre")
@CrossOrigin(origins = "http://localhost:4200")

public class ChambreController {
    @Autowired
    ChambreServiceImp iChambreService;
    @PostMapping("/addChambre")
    Chambre addChambre(@RequestBody  Chambre chambre){
        return iChambreService.addChambre(chambre);
    }
    @GetMapping("/allChambre")
    List<Chambre>allChambre(){
        return iChambreService.getChambre();
    }

    @GetMapping("/chambre/{id}")
    Chambre retrieveBloc(@PathVariable Long id)
    {
        return iChambreService.getChambreById(id);
    }

    @GetMapping("/chambreBloc/{id}")
    Bloc retrieveChambreBloc(@PathVariable Long id)
    {
        Chambre chambre = iChambreService.getChambreById(id);
        return chambre.getBloc();
    }

    @PutMapping("/updateChambre")
    Chambre updateChambre(@RequestBody Chambre chambre)
    {
        return  iChambreService.update(chambre);
    }
    @PutMapping("/updateChambre/{id}")
    public Chambre updateChambreId(@RequestBody Chambre chambre,@PathVariable Long id) {
        chambre.setIdChambre(id);
        return iChambreService.update(chambre);
    }
    @DeleteMapping("/deleteChambre/{id}")
    void deleteChambre(@PathVariable Long id)
    {
        iChambreService.delete(id);
    }
    @DeleteMapping("/deleteChambre")
    void deleteChambre(@RequestBody Chambre chambre) {
        iChambreService.deleteChambre(chambre);
    }
    @PutMapping("/affecterChambreABloc/{idChambre}/{idBloc}")
    void affecterChambreABloc(@PathVariable long idChambre, @PathVariable long idBloc)
    {
        iChambreService.AffecterChambreABloc(idChambre,idBloc);
    }
    @PutMapping("/desaffecterChambreABloc/{idChambre}")
    void desaffecterChambreABloc(@PathVariable long idChambre)
    {
         iChambreService.desaffecterChambreDeBloc(idChambre);
    }


    @GetMapping("/qr/{idChambre}")
    public void generateQRCode(@PathVariable Long idChambre, HttpServletResponse response) {
        try {
            Chambre chambre = iChambreService.getChambreById(idChambre);
            BufferedImage bufferedImage = generateQRCodeImage(chambre);

            // Set the content type and headers
            response.setContentType("image/jpeg");
            response.setHeader("Content-Disposition", "inline; filename=qrCode.jpg");

            // Write the image to the response's output stream
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately, e.g., return an error response
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public static BufferedImage generateQRCodeImage(Chambre chambre) throws Exception {
        if (chambre != null) {
            String qrContent = String.format("Numero Chambre: %d | Type: %s", chambre.getNumeroChambre(), chambre.getTypeC());

            QRCodeWriter barcodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = barcodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 200, 200);

            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        }
        return null;
    }

    @GetMapping("/by-bloc/{blocId}")
    public List<Chambre> getChambresByBloc(@PathVariable Long blocId) {
        Bloc bloc = new Bloc();
        bloc.setIdBloc(blocId);
        return iChambreService.getChambresByBloc(bloc);
    }

    @GetMapping("/by-type/{typeChambre}")
    public List<Chambre> getChambresByType(@PathVariable TypeChambre typeChambre) {
        return iChambreService.getChambresByType(typeChambre);
    }




}

