--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements
DELETE FROM rol;
INSERT INTO `ordenes`.`rol` (`id`, `descripcion`, `rol`) VALUES ('1', 'ADMIN', 'ADMIN');
INSERT INTO `ordenes`.`rol` (`id`, `descripcion`, `rol`) VALUES ('2', 'BASICO', 'BASICO');
DELETE FROM usuarios;
INSERT INTO `ordenes`.`usuarios` (`id`, `correo`, `nombre`, `password`, `telefono`, `usuario`, `rol`) VALUES ('1', 'admin@workwlow.com.uy', 'admin', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', '123', 'admin', '1');