/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Team;
import java.io.File;

/**
 *
 * @author dtjldamien
 */
public class UploadFileReq {

    private Long studentId;
    private File resume;

    public UploadFileReq() {
    }

    public UploadFileReq(Long studentId, File resume) {
        this.studentId = studentId;
        this.resume = resume;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public File getResume() {
        return resume;
    }

    public void setResume(File resume) {
        this.resume = resume;
    }
}
