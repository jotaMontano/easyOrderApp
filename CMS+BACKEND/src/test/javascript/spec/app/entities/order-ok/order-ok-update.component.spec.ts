/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EasyOrderTestModule } from '../../../test.module';
import { OrderOkUpdateComponent } from 'app/entities/order-ok/order-ok-update.component';
import { OrderOkService } from 'app/entities/order-ok/order-ok.service';
import { OrderOk } from 'app/shared/model/order-ok.model';

describe('Component Tests', () => {
    describe('OrderOk Management Update Component', () => {
        let comp: OrderOkUpdateComponent;
        let fixture: ComponentFixture<OrderOkUpdateComponent>;
        let service: OrderOkService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EasyOrderTestModule],
                declarations: [OrderOkUpdateComponent]
            })
                .overrideTemplate(OrderOkUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrderOkUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderOkService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OrderOk(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orderOk = entity;
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
                    const entity = new OrderOk();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orderOk = entity;
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
