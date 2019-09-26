/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EasyOrderTestModule } from '../../../test.module';
import { TopUpdateComponent } from 'app/entities/top/top-update.component';
import { TopService } from 'app/entities/top/top.service';
import { Top } from 'app/shared/model/top.model';

describe('Component Tests', () => {
    describe('Top Management Update Component', () => {
        let comp: TopUpdateComponent;
        let fixture: ComponentFixture<TopUpdateComponent>;
        let service: TopService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EasyOrderTestModule],
                declarations: [TopUpdateComponent]
            })
                .overrideTemplate(TopUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TopUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TopService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Top(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.top = entity;
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
                    const entity = new Top();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.top = entity;
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
