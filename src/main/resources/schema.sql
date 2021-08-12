CREATE table if not exists public.subject (
	id int NOT NULL,
	description varchar(200) NOT NULL,
	CONSTRAINT subject_pk PRIMARY KEY (id)
);


CREATE table if not exists session (
  id int NOT NULL,
  start_date_time timestamp NOT NULL,
  end_date_time timestamp NOT NULL,
  subject_id int NOT NULL,
  CONSTRAINT session_pk PRIMARY KEY (id),
  CONSTRAINT session_subject_fk FOREIGN KEY ("subject_id") REFERENCES public.subject(id)
);



CREATE table if not exists member (
  id int NOT NULL,
  CONSTRAINT member_pk PRIMARY KEY (id)
);



CREATE table if not exists vote (
  id int NOT NULL,
  choice bool NOT NULL,
  session_id int NOT NULL,
  member_id int NOT NULL,
  CONSTRAINT vote_pk PRIMARY KEY (id),
  CONSTRAINT vote_session_fk FOREIGN KEY ("session_id") REFERENCES public.session(id),
  CONSTRAINT vote_member_fk FOREIGN KEY ("member_id") REFERENCES public.member(id)
);


CREATE SEQUENCE IF NOT EXISTS public.hibernate_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;