/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.workers.units.reportes;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author Carlos
 */
@Component
public class MongoFileRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Guarda un archivo dentro del grid file system de mongo db
     * 
     * @param filename el nombre del archivo
     * @param content  el contenido del archivo un array de bytes
     * @param repository el nombre del repositorio por ejemplo: reportes
     */
    public void saveFile(String filename,byte[] content,String repository) {
        GridFS gfsReportes = new GridFS(mongoTemplate.getDb(), repository);
        GridFSInputFile gfsFile = gfsReportes.createFile(content);
        gfsFile.setFilename(filename);
        //gfsFile.setContentType(formatName.toLowerCase());
        gfsFile.save();
    }
}
