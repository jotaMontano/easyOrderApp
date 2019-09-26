/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EasyOrderTestModule } from '../../../test.module';
import { ProductByOrderUpdateComponent } from 'app/entities/product-by-order/product-by-order-update.component';
import { ProductByOrderService } from 'app/entities/product-by-order/product-by-order.service';
import { ProductByOrder } from 'app/shared/model/product-by-order.model';

describe('Component Tests', () => {
    describe('ProductByOrder Management Update Component', () => {
        let comp: ProductByOrderUpdateComponent;
        let fixture: ComponentFixture<ProductByOrderUpdateComponent>;
        let service: ProductByOrderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EasyOrderTestModule],
                declarations: [ProductByOrderUpdateComponent]
            })
                .overrideTemplate(ProductByOrderUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductByOrderUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductByOrderService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProductByOrder(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productByOrder = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProductByOrder();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productByOrder = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
