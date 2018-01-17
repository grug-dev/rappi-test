import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs/Observable';
import { FileItem } from '../models/file-item';
import 'rxjs/add/operator/catch'; 


@Injectable()
export class SendFileService {



  constructor(private http: HttpClient) {

  }

  processFile(fileItem: FileItem): Observable<any> {
    const formData = new FormData();
    formData.append('file', fileItem.archivo);
    const url: string = environment.HTTP_URL + 'cube/upload';

     return this.http.post(url, formData).catch((err: HttpErrorResponse) => {        
        console.error('Ha ocurrido un error invocando el servicio:', err.error);
        return err;
      });;
  }

}
