import { Route } from '@angular/router';

import { DomainListComponent } from './domain-list/domain-list.component';

export const ROUTES: Route[] = [
    {
        path: '',
        component: DomainListComponent,
    }
];
