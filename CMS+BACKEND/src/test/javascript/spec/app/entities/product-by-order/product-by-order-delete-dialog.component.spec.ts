/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EasyOrderTestModule } from '../../../test.module';
import { ProductByOrderDeleteDialogComponent } from 'app/entities/product-by-order/product-by-order-delete-dialog.component';
import { ProductByOrderService } from 'app/entities/product-by-order/product-by-order.service';

describe('Component Tests', () => {
    describe('ProductByOrder Management Delete Component', () => {
        let comp: ProductByOrderDeleteDialogComponent;
        let fixture: ComponentFixture<ProductByOrderDeleteDialogComponent>;
        let service: ProductByOrderService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EasyOrderTestModule],
                declarations: [ProductByOrderDeleteDialogComponent]
            })
                .overrideTemplate(ProductByOrderDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductByOrderDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductByOrderService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
