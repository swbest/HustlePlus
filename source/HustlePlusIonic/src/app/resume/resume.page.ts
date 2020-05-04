import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { FileTransfer, FileUploadOptions, FileTransferObject } from '@ionic-native/file-transfer/ngx';
import { FileChooser } from '@ionic-native/file-chooser/ngx'
import { FilePath } from '@ionic-native/file-path/ngx'
import { File } from '@ionic-native/file/ngx'

import { FileUploadService } from '../file-upload.service';
import { SessionService } from '../session.service';

@Component({
  selector: 'app-resume',
  templateUrl: './resume.page.html',
  styleUrls: ['./resume.page.scss'],
})

export class ResumePage implements OnInit {

  baseUrl: string;
  studentId: number;
  uploadText: any;
  downloadText: any;
  fileTransfer: FileTransferObject;
  fileToUpload: File;

  constructor(private router: Router,
    private transfer: FileTransfer,
    private file: File,
    private filePath: FilePath,
    private fileChooser: FileChooser,
    private fileUploadService: FileUploadService,
    private sessionService: SessionService) {
    this.uploadText = "";
    this.downloadText = "";
    this.fileToUpload = null;
  }

  ngOnInit() {
  }

  uploadFile() {
    this.baseUrl = this.sessionService.getRootPath() + 'File/uploadResume' + this.sessionService.getCurrentStudent().userId;
    this.fileChooser.open().then((uri) => {
      this.filePath.resolveNativePath(uri).then((nativepath) => {
        this.fileTransfer = this.transfer.create();
        let options: FileUploadOptions = {
          fileKey: 'resume',
          fileName: this.sessionService.getCurrentStudent().username + '\'s resume.pdf',
          chunkedMode: false,
          headers: {},
          mimeType: 'application/pdf'
        }
        this.uploadText = "Uploading your resume to our servers!";
        //  upload method need to refine
        this.fileTransfer.upload(nativepath, this.baseUrl, options).then((data) => {
          alert("Upload done = " + JSON.stringify(data));
          this.uploadText = "";
        })
      }, (err) => {
        alert(JSON.stringify(err));
      })
    }, (err) => {
      alert(JSON.stringify(err));
    })
  }

  abortUpload() {
    this.fileTransfer.abort();
    alert("Cancelling upload of resume!");
  }

  back() {
    this.router.navigate(["/profile"]);
  }
}
