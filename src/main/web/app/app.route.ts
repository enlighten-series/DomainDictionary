import { Route } from '@angular/router';

import { DomainListComponent } from './domain-list/domain-list.component';
import { DomainCreateComponent } from './domain-create/domain-create.component';
import { DomainDetailComponent } from './domain-detail/domain-detail.component';
import { LicenseListComponent } from './license-list/license-list.component';
import { ErrorPageComponent } from './error-page/error-page.component';

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
    {
        path: 'license',
        component: LicenseListComponent,
    },
    {
        path: '404',
        component: ErrorPageComponent,
        data: {
            status: '404'
        },
    },
    {
        path: '**',
        component: ErrorPageComponent,
    },
];
