import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { CargarArchivoComponent } from './components/cargar-archivo/cargar-archivo.component';
import { DropFileDirective } from './directive/drop-file.directive';
import { SendFileService } from './services/send-file.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatrixPipe } from './pipes/matrix.pipe';
import { TxtAreaResultsPipe } from './pipes/txtresults.pipe';
import { FilesWaitingPipe } from './pipes/files-waiting.pipe';


@NgModule({
  declarations: [
    AppComponent,
    CargarArchivoComponent,
    DropFileDirective,
    MatrixPipe,
    TxtAreaResultsPipe,
    FilesWaitingPipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    SendFileService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
