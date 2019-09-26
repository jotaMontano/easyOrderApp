/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EasyOrderTestModule } from '../../../test.module';
import { OrderOkDeleteDialogComponent } from 'app/entities/order-ok/order-ok-delete-dialog.component';
import { OrderOkService } from 'app/entities/order-ok/order-ok.service';

describe('Component Tests', () => {
    describe('OrderOk Management Delete Component', () => {
        let comp: OrderOkDeleteDialogComponent;
        let fixture: ComponentFixture<OrderOkDeleteDialogComponent>;
        let service: OrderOkService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EasyOrderTestModule],
                declarations: [OrderOkDeleteDialogComponent]
            })
                .overrideTemplate(OrderOkDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrderOkDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderOkService);
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
