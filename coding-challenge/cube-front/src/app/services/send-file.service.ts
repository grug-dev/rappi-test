import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs/Observable';
import { FileItem } from '../models/file-item';



@Injectable()
export class SendFileService {



  constructor(private http: HttpClient) {

  }

  processFile(fileItem: FileItem) {
    const formData = new FormData();
    formData.append('file', fileItem.archivo);
    const url: string = environment.HTTP_URL + 'cube/upload';

     const headers = new HttpHeaders(
       {
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET, POST, OPTIONS, PUT, PATCH, DELETE',
      'Access-Control-Allow-Headers': 'X-Requested-With,content-type',
      'Access-Control-Allow-Credentials': true
    });


      return this.http.post(url, formData);
  }

}
