/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EasyOrderTestModule } from '../../../test.module';
import { ExtraDeleteDialogComponent } from 'app/entities/extra/extra-delete-dialog.component';
import { ExtraService } from 'app/entities/extra/extra.service';

describe('Component Tests', () => {
    describe('Extra Management Delete Component', () => {
        let comp: ExtraDeleteDialogComponent;
        let fixture: ComponentFixture<ExtraDeleteDialogComponent>;
        let service: ExtraService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EasyOrderTestModule],
                declarations: [ExtraDeleteDialogComponent]
            })
                .overrideTemplate(ExtraDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExtraDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExtraService);
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
