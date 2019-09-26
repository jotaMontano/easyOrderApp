/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EasyOrderTestModule } from '../../../test.module';
import { OrderOkDetailComponent } from 'app/entities/order-ok/order-ok-detail.component';
import { OrderOk } from 'app/shared/model/order-ok.model';

describe('Component Tests', () => {
    describe('OrderOk Management Detail Component', () => {
        let comp: OrderOkDetailComponent;
        let fixture: ComponentFixture<OrderOkDetailComponent>;
        const route = ({ data: of({ orderOk: new OrderOk(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EasyOrderTestModule],
                declarations: [OrderOkDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OrderOkDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrderOkDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.orderOk).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
