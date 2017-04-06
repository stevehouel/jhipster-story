import { Component } from '@angular/core';
import { Response } from '@angular/http';
import { JhiLanguageService } from 'ng-jhipster';

import { GraphService } from './graph.service';

@Component({
    selector: 'jhi-graph',
    templateUrl: './graph.component.html',
    styleUrls: [
        'graph.css'
    ]

})
export class GraphComponent {
    private chart : any;
    private options: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private graphService: GraphService
    ) {
        this.jhiLanguageService.setLocations(['global']);
        this.options = {
          chart: { type: 'spline' },
          title: { text : 'Lies'},
          series: [{ data: [] }]
        };
        setInterval(() => {
            this.graphService.get().subscribe(
               (res: Response) => this.chart.series[0].addPoint(res.json()),
               (res: Response) => this.chart.series[0].addPoint(0)
           );
        }, 2000);
    }

    saveInstance(chartInstance) {
        this.chart = chartInstance;
    }
}
