import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'matrix'
})
export class MatrixPipe implements PipeTransform {

  transform(value: number, args?: any): string {
    return `${value}x${value}x${value}`;
  }

}
