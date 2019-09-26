/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EasyOrderTestModule } from '../../../test.module';
import { ListOfValueUpdateComponent } from 'app/entities/list-of-value/list-of-value-update.component';
import { ListOfValueService } from 'app/entities/list-of-value/list-of-value.service';
import { ListOfValue } from 'app/shared/model/list-of-value.model';

describe('Component Tests', () => {
    describe('ListOfValue Management Update Component', () => {
        let comp: ListOfValueUpdateComponent;
        let fixture: ComponentFixture<ListOfValueUpdateComponent>;
        let service: ListOfValueService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EasyOrderTestModule],
                declarations: [ListOfValueUpdateComponent]
            })
                .overrideTemplate(ListOfValueUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ListOfValueUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ListOfValueService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ListOfValue(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.listOfValue = entity;
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
                    const entity = new ListOfValue();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.listOfValue = entity;
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
