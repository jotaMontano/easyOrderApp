/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EasyOrderTestModule } from '../../../test.module';
import { ExtraInLineDeleteDialogComponent } from 'app/entities/extra-in-line/extra-in-line-delete-dialog.component';
import { ExtraInLineService } from 'app/entities/extra-in-line/extra-in-line.service';

describe('Component Tests', () => {
    describe('ExtraInLine Management Delete Component', () => {
        let comp: ExtraInLineDeleteDialogComponent;
        let fixture: ComponentFixture<ExtraInLineDeleteDialogComponent>;
        let service: ExtraInLineService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EasyOrderTestModule],
                declarations: [ExtraInLineDeleteDialogComponent]
            })
                .overrideTemplate(ExtraInLineDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExtraInLineDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExtraInLineService);
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
