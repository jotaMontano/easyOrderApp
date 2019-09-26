/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EasyOrderTestModule } from '../../../test.module';
import { ExtraInLineDetailComponent } from 'app/entities/extra-in-line/extra-in-line-detail.component';
import { ExtraInLine } from 'app/shared/model/extra-in-line.model';

describe('Component Tests', () => {
    describe('ExtraInLine Management Detail Component', () => {
        let comp: ExtraInLineDetailComponent;
        let fixture: ComponentFixture<ExtraInLineDetailComponent>;
        const route = ({ data: of({ extraInLine: new ExtraInLine(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EasyOrderTestModule],
                declarations: [ExtraInLineDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ExtraInLineDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExtraInLineDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.extraInLine).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
