import { Directive, ElementRef, EventEmitter, HostListener, Input, Output } from '@angular/core';
import { FileItem } from '../models/file-item';

@Directive({
  selector: "[appDropFile]"
})
export class DropFileDirective {
  constructor() {}

  @Output() mouseSobre: EventEmitter<boolean> = new EventEmitter();

  @Input() archivodropped: FileItem ;

  @Input() archivos: FileItem[] = [];

  @HostListener("dragover", ["$event"])
  public onDragEnter(event: any) {
    this.mouseSobre.emit(true);
    this._prevenirDetener(event);
  }

  @HostListener("dragleave", ["$event"])
  public onDragLeave(event: any) {
    this.mouseSobre.emit(false);
  }

  @HostListener("drop", ["$event"])
  public onDrop(event: any) {
    this.mouseSobre.emit(false);

    // Aqui tengo la informacion dropeada
    const transferencia = this.getTransferencia(event);
    if (!transferencia) {
      return;
    }

    this.extraerArchivos(transferencia.files);
    this._prevenirDetener(event);
    this.mouseSobre.emit(false);
  }

  /**
   * El archivo se obtiene del dataTransfer, pero en ocasiones los navegadores lo contienen en originalEvent.
   * Este metodo se realiza por compatibilidad
   * @param event
   */
  private getTransferencia(event: any) {
    return event.dataTransfer
      ? event.dataTransfer
      : event.originalEvent.dataTransfer;
  }

  private extraerArchivos(lstArchivos: FileList) {
    // tslint:disable-next-line:forin
    for (const propiedad in Object.getOwnPropertyNames(lstArchivos)) {
      const archivoTmp = lstArchivos[propiedad];
      if (this._archivoPuedeSerCargado(archivoTmp)) {
        this.archivodropped = new FileItem(archivoTmp);
        this.archivos.push(this.archivodropped);
      }
    }
  }

  // Validaciones
  private _archivoPuedeSerCargado(archivo: File): boolean {
    if (this.isFile(archivo.type)) {
      return true;
    }

    return false;
  }

  /**
   * Prevenir que se cargue la imagen automaticamente cuando se suelta.
   */
  private _prevenirDetener(event) {
    event.preventDefault();
    event.stopPropagation();
  }

  private isFile(pTipoArchivo: string): boolean {
    return pTipoArchivo === '' || pTipoArchivo === undefined
      ? false
      : pTipoArchivo.startsWith('text/plain');
  }
}
