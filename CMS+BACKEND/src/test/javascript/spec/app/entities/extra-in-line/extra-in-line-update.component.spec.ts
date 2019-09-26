/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EasyOrderTestModule } from '../../../test.module';
import { ExtraInLineUpdateComponent } from 'app/entities/extra-in-line/extra-in-line-update.component';
import { ExtraInLineService } from 'app/entities/extra-in-line/extra-in-line.service';
import { ExtraInLine } from 'app/shared/model/extra-in-line.model';

describe('Component Tests', () => {
    describe('ExtraInLine Management Update Component', () => {
        let comp: ExtraInLineUpdateComponent;
        let fixture: ComponentFixture<ExtraInLineUpdateComponent>;
        let service: ExtraInLineService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EasyOrderTestModule],
                declarations: [ExtraInLineUpdateComponent]
            })
                .overrideTemplate(ExtraInLineUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExtraInLineUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExtraInLineService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ExtraInLine(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.extraInLine = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ExtraInLine();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.extraInLine = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
