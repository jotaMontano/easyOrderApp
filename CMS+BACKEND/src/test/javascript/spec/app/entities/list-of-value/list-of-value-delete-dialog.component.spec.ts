/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EasyOrderTestModule } from '../../../test.module';
import { ListOfValueDeleteDialogComponent } from 'app/entities/list-of-value/list-of-value-delete-dialog.component';
import { ListOfValueService } from 'app/entities/list-of-value/list-of-value.service';

describe('Component Tests', () => {
    describe('ListOfValue Management Delete Component', () => {
        let comp: ListOfValueDeleteDialogComponent;
        let fixture: ComponentFixture<ListOfValueDeleteDialogComponent>;
        let service: ListOfValueService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EasyOrderTestModule],
                declarations: [ListOfValueDeleteDialogComponent]
            })
                .overrideTemplate(ListOfValueDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ListOfValueDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ListOfValueService);
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
