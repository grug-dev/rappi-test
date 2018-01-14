import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'txtarearesults'
})
export class TxtAreaResultsPipe implements PipeTransform {

  transform(value: any[], args?: any): string {
    let result: string = '';

    if (!value) {
      return result;
    }

    for ( let r of value) {
      result += `${r}\n`;
    }
    return result;
  }

}
