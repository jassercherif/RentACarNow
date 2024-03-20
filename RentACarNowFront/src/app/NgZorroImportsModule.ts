import { NgModule } from '@angular/core';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzFormModule } from 'ng-zorro-antd/form';


@NgModule({
    exports:[
       NzSpinModule,
       NzInputModule,
       NzLayoutModule,
       NzButtonModule,
       NzFormModule
    ]
})

export class NgZorroImportsModule {}