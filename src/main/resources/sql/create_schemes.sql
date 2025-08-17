-- ENUM tipleri
CREATE TYPE currency_enum AS ENUM ('USD','EUR','GBP','INR','JPY','CNY','KRW','TRY');
CREATE TYPE attribute_type_enum AS ENUM ('TEXT','NUMBER','BOOLEAN','SELECT','MULTI_SELECT','DATE','COLOR');
CREATE TYPE product_status_enum AS ENUM ('DRAFT','ACTIVE','INACTIVE','OUT_OF_STOCK','DISCONTINUED');
CREATE TYPE brand_status_enum AS ENUM ('ACTIVE','INACTIVE','PENDING_APPROVAL');
CREATE TYPE supplier_status_enum AS ENUM ('PENDING','ACTIVE','SUSPENDED','TERMINATED');
CREATE TYPE supplier_type_enum AS ENUM ('MANUFACTURER','DISTRIBUTOR','WHOLESALER','RETAILER','DROPSHIPPER');
CREATE TYPE payment_method_enum AS ENUM ('BANK_TRANSFER','CREDIT_CARD','CASH','CHECK','CRYPTOCURRENCY');

-- BRAND
CREATE TABLE brand (
                       id                  uuid PRIMARY KEY,
                       name                text NOT NULL,
                       slug                text UNIQUE,
                       description         text,
                       logo_url            text,
                       banner_url          text,
                       website_url         text,
                       origin_country      text,
                       established_year    integer,
                       created_at          timestamp without time zone,
                       updated_at          timestamp without time zone,
                       social_media        jsonb,
                       status              brand_status_enum NOT NULL DEFAULT 'ACTIVE',
                       is_featured         boolean,
                       sort_order          integer,
                       meta_title          text,
                       meta_keywords       text
);

-- CATEGORY
CREATE TABLE category (
                          id                  uuid PRIMARY KEY,
                          name                text NOT NULL,
                          slug                text UNIQUE,
                          description         text,
                          icon_url            text,
                          image_url           text,
                          banner_url          text,
                          created_at          timestamp without time zone,
                          updated_at          timestamp without time zone,
                          parent_id           uuid REFERENCES category(id) ON DELETE SET NULL,
                          level               integer DEFAULT 0,
                          sort_order          integer DEFAULT 0,
                          is_active           boolean DEFAULT true,
                          is_featured         boolean DEFAULT false,
                          show_in_menu        boolean DEFAULT true,
                          commission_rate     numeric(5,2),
                          meta_title          text,
                          meta_description    text,
                          meta_keywords       text
);

CREATE INDEX idx_category_parent ON category(parent_id);

-- CATEGORY_ATTRIBUTE
CREATE TABLE category_attribute (
                                    id              bigserial PRIMARY KEY,
                                    name            text NOT NULL,
                                    display_name    text,
                                    type            attribute_type_enum NOT NULL,
                                    is_required     boolean,
                                    is_filterable   boolean,
                                    is_searchable   boolean,
                                    options         text[],
                                    unit            text,
                                    sort_order      integer
);

-- CATEGORY <-> CATEGORY_ATTRIBUTE (N:N)
CREATE TABLE category_category_attribute (
                                             category_id     uuid NOT NULL REFERENCES category(id) ON DELETE CASCADE,
                                             attribute_id    bigint NOT NULL REFERENCES category_attribute(id) ON DELETE CASCADE,
                                             PRIMARY KEY (category_id, attribute_id)
);

-- PRODUCT_DIMENSION
CREATE TABLE product_dimension (
                                   id          bigserial PRIMARY KEY,
                                   length      numeric(19,2),
                                   width       numeric(19,2),
                                   height      numeric(19,2),
                                   volume      numeric(19,2)
);

-- PRODUCT_IMAGE
CREATE TABLE product_image (
                               id          bigserial PRIMARY KEY,
                               url         text NOT NULL,
                               alt_text    text,
                               caption     text,
                               sort_order  integer,
                               is_primary  boolean,
                               size        text,
                               mime_type   text,
                               file_size   bigint
);

-- SUPPLIER_CONTACT_INFO
CREATE TABLE supplier_contact_info (
                                       id                      uuid PRIMARY KEY,
                                       primary_contact_name    text,
                                       primary_contact_title   text,
                                       primary_email           text,
                                       primary_phone           text,
                                       secondary_contact_name  text,
                                       secondary_email         text,
                                       secondary_phone         text,
                                       website                 text,
                                       fax                     text
);

-- ADDRESS
CREATE TABLE address (
                         id          uuid PRIMARY KEY,
                         street      text,
                         district    text,
                         city        text,
                         state       text,
                         country     text,
                         postal_code text,
                         latitude    text,
                         longitude   text,
                         is_default  boolean
);

-- BANK_INFO
CREATE TABLE bank_info (
                           id                  uuid PRIMARY KEY,
                           bank_name           text,
                           branch_name         text,
                           branch_code         text,
                           account_number      text,
                           iban                text,
                           swift_code          text,
                           account_holder_name text,
                           account_type        text,
                           currency            currency_enum,
                           is_active           boolean
);

-- SUPPLIER
CREATE TABLE supplier (
                          id                       uuid PRIMARY KEY,
                          code                     text UNIQUE,
                          company_name             text,
                          trade_name               text,
                          tax_number               text,
                          tax_office               text,
                          supplier_type            supplier_type_enum,
                          contact_info_id          uuid REFERENCES supplier_contact_info(id) ON DELETE SET NULL,
                          address_id               uuid REFERENCES address(id) ON DELETE SET NULL,
                          created_at               timestamp without time zone,
                          updated_at               timestamp without time zone,
                          status                   supplier_status_enum NOT NULL DEFAULT 'PENDING',
                          credit_limit             numeric(19,2) DEFAULT 0,
                          payment_terms_days       integer,
                          preferred_payment_method payment_method_enum,
                          currency                 currency_enum NOT NULL DEFAULT 'TRY',
                          lead_time_days           integer,
                          minimum_order_amount     numeric(19,2),
                          discount_rate            numeric(5,2) DEFAULT 0 CHECK (discount_rate >= 0 AND discount_rate <= 100),
                          rating                   numeric(3,2) CHECK (rating >= 0 AND rating <= 5),
                          total_orders             integer DEFAULT 0,
                          total_purchase_amount    numeric(19,2) DEFAULT 0,
                          last_order_date          timestamp without time zone,
                          contract_start_date      date,
                          contract_end_date        date,
                          notes                    text
);

CREATE INDEX idx_supplier_contact_info ON supplier(contact_info_id);
CREATE INDEX idx_supplier_address ON supplier(address_id);

-- SUPPLIER <-> BANK_INFO (N:N)
CREATE TABLE supplier_bank_infos (
                                     supplier_id     uuid NOT NULL REFERENCES supplier(id) ON DELETE CASCADE,
                                     bank_info_id    uuid NOT NULL REFERENCES bank_info(id) ON DELETE CASCADE,
                                     PRIMARY KEY (supplier_id, bank_info_id)
);

-- SUPPLIER <-> CATEGORY (N:N)
CREATE TABLE supplier_categories (
                                     supplier_id     uuid NOT NULL REFERENCES supplier(id) ON DELETE CASCADE,
                                     category_id     uuid NOT NULL REFERENCES category(id) ON DELETE CASCADE,
                                     PRIMARY KEY (supplier_id, category_id)
);

-- SUPPLIER certifications (1:N basit liste)
CREATE TABLE supplier_certifications (
                                         supplier_id     uuid NOT NULL REFERENCES supplier(id) ON DELETE CASCADE,
                                         certification   text NOT NULL,
                                         PRIMARY KEY (supplier_id, certification)
);

-- PRODUCT
CREATE TABLE product (
                         id                          uuid PRIMARY KEY,
                         sku                         text UNIQUE,
                         name                        text NOT NULL,
                         description                 text,
                         short_description           text,
                         slug                        text UNIQUE,
                         created_at                  timestamp without time zone,
                         updated_at                  timestamp without time zone,
                         brand_id                    uuid REFERENCES brand(id) ON DELETE SET NULL,
                         category_id                 uuid REFERENCES category(id) ON DELETE SET NULL,
                         supplier_id                 uuid REFERENCES supplier(id) ON DELETE SET NULL,
                         base_price                  numeric(19,2),
                         cost_price                  numeric(19,2),
                         weight                      numeric(19,3),
                         dimension_id                bigint REFERENCES product_dimension(id) ON DELETE SET NULL,
                         attributes                  jsonb,
                         status                      product_status_enum NOT NULL DEFAULT 'DRAFT',
                         is_featured                 boolean,
                         is_digital                  boolean,
                         requires_shipping           boolean,
                         tax_rate                    numeric(5,2) CHECK (tax_rate IS NULL OR (tax_rate >= 0 AND tax_rate <= 100)),
                         min_order_quantity          integer DEFAULT 1,
                         max_order_quantity          integer,
                         meta_title                  text,
                         meta_description            text,
                         meta_keywords               text,
                         barcode                     text,
                         manufacturer_part_number    text,
                         warranty_period_months      integer,
                         launch_date                 date,
                         discontinue_date            date
);

CREATE INDEX idx_product_brand ON product(brand_id);
CREATE INDEX idx_product_category ON product(category_id);
CREATE INDEX idx_product_supplier ON product(supplier_id);
CREATE INDEX idx_product_dimension ON product(dimension_id);

-- PRODUCT <-> PRODUCT_IMAGE (N:N)
CREATE TABLE product_product_image (
                                       product_id  uuid NOT NULL REFERENCES product(id) ON DELETE CASCADE,
                                       image_id    bigint NOT NULL REFERENCES product_image(id) ON DELETE CASCADE,
                                       PRIMARY KEY (product_id, image_id)
);
