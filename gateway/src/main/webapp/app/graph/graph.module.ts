import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../shared';

import { GRAPH_ROUTE, GraphComponent, GraphService } from './';

import { ChartModule } from 'angular2-highcharts';

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forRoot([ GRAPH_ROUTE ], { useHash: true }),
        ChartModule.forRoot(require('highcharts')),
    ],
    declarations: [
        GraphComponent,
    ],
    entryComponents: [
    ],
    providers: [
        GraphService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayGraphModule {}
