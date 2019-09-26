/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EasyOrderTestModule } from '../../../test.module';
import { ExtraUpdateComponent } from 'app/entities/extra/extra-update.component';
import { ExtraService } from 'app/entities/extra/extra.service';
import { Extra } from 'app/shared/model/extra.model';

describe('Component Tests', () => {
    describe('Extra Management Update Component', () => {
        let comp: ExtraUpdateComponent;
        let fixture: ComponentFixture<ExtraUpdateComponent>;
        let service: ExtraService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EasyOrderTestModule],
                declarations: [ExtraUpdateComponent]
            })
                .overrideTemplate(ExtraUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExtraUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExtraService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Extra(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.extra = entity;
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
                    const entity = new Extra();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.extra = entity;
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
