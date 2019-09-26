/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EasyOrderTestModule } from '../../../test.module';
import { ProductByOrderDetailComponent } from 'app/entities/product-by-order/product-by-order-detail.component';
import { ProductByOrder } from 'app/shared/model/product-by-order.model';

describe('Component Tests', () => {
    describe('ProductByOrder Management Detail Component', () => {
        let comp: ProductByOrderDetailComponent;
        let fixture: ComponentFixture<ProductByOrderDetailComponent>;
        const route = ({ data: of({ productByOrder: new ProductByOrder(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EasyOrderTestModule],
                declarations: [ProductByOrderDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductByOrderDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductByOrderDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productByOrder).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
