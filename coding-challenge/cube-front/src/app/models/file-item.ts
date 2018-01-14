export class FileItem {

    public static  OK = 'OK';
    public static  PENDIENTE = 'PENDIENTE';

    public archivo: File;

    public nombreArchivo: string = '';

    public resultado: string;

    constructor(pArchivo: File) {
        this.archivo = pArchivo;
        this.nombreArchivo = this.archivo.name;
        this.resultado = FileItem.PENDIENTE;
    }

}
