import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { GraphComponent } from './';

export const GRAPH_ROUTE: Route = {
  path: 'graph',
  component: GraphComponent,
  data: {
    authorities: [],
    pageTitle: 'graph.title'
  },
  canActivate: [UserRouteAccessService]
};
