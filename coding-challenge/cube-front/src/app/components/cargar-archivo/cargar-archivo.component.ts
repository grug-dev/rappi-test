import { Component, OnInit } from '@angular/core';
import { FileItem } from '../../models/file-item';
import { SendFileService } from '../../services/send-file.service';
import { Cube } from '../../models/cube';
import { CubeTestCases } from '../../models/cubetestcases';

@Component({
  selector: 'app-cargar-archivo',
  templateUrl: './cargar-archivo.component.html',
  styleUrls: ['./cargar-archivo.component.css']
})
export class CargarArchivoComponent implements OnInit {

  archivos: FileItem[] = [];
  currentfile: FileItem ;
  isOverElement = false;
  txtResults: any;
  results: CubeTestCases;

  constructor(private sendFileService: SendFileService) { }

  ngOnInit() {
   console.log(this.currentfile);
  }

  public subirArchivo() {
    for (const archivo of this.archivos) {
      if (archivo.resultado === FileItem.PENDIENTE) {
        this.currentfile = archivo;
        break;
      }
    }
    this.sendFileService.processFile(this.currentfile)
                    .subscribe( resp => {
                      this.results = resp;
                      this.currentfile.resultado = FileItem.OK;
                      if (this.results.status === 200) {                        
                        this.txtResults = this.results.lstResults;                        
                      } else {                       
                        this.currentfile = null;
                        this.results.lstCases = [];                        
                      }                      
                    }, error => {
                      this.results = { status: 500 , msg: "El servicio no se encuentra disponible." , lstCases: [] , lstResults: [] } ;                      
                    });
  }

  public limpiar() {
    this.archivos = [];
    this.results.lstCases = [];
    this.results.msg = '';
    this.results.status = -1;
    this.txtResults = '';
    this.currentfile = null;
  }

}
