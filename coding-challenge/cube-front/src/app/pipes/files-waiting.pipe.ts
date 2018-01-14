import { Pipe, PipeTransform } from '@angular/core';
import { FileItem } from '../models/file-item';


@Pipe({
  name: 'filesWaiting',
  pure: false
})
export class FilesWaitingPipe implements PipeTransform {

  transform(value: FileItem[], args?: any): any {
    if (!value) {
      return false;
    }
    for (const archivo of  value) {
      if (archivo.resultado === FileItem.PENDIENTE) {
        return true;
      }
    }
    return false;
  }

}
