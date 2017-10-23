import { Route } from '@angular/router';

import { DomainListComponent } from './domain-list/domain-list.component';
import { DomainCreateComponent } from './domain-create/domain-create.component';
import { DomainDetailComponent } from './domain-detail/domain-detail.component';

export const ROUTES: Route[] = [
    {
        path: '',
        component: DomainListComponent,
    },
    {
        path: 'create',
        component: DomainCreateComponent,
    },
    {
        path: 'detail/:id',
        component: DomainDetailComponent,
    },
];
