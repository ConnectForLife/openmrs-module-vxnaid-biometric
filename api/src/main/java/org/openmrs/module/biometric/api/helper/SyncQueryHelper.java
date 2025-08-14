/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * OpenMRS is also distributed under the terms of the Healthcare Disclaimer located at
 * http://openmrs.org/license.
 *
 * <p>Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS graphic logo is
 * a trademark of OpenMRS Inc.
 */

package org.openmrs.module.biometric.api.helper;

/**
 * Helper class for Sync queries.
 */
public final class SyncQueryHelper {

  private static final String TEMPLATES_SQL = "SELECT dbid, TO_BASE64(template) template FROM"
      + " iris_templates where dbid in (:patientIdentifierList) ";

  private static final String SYNC_IGNORED_COUNT_QUERY =
      "select count(person.id) "
          + "from Person as person "
          + "inner join person.attributes as attribute "
          + " where attribute.voided = 0 and attribute.attributeType.name = :attributeTypeName "
          + " and person.id in ("
          + "   select p.id from Person as p "
          + "   inner join p.attributes as pa "
          + "   where pa.voided = 0 and pa.attributeType.name='LocationAttribute' "
          + "     and pa.value in (:locations)"
          + "     and attribute.value = :deviceId)";

  private static final String SYNC_TOTAL_COUNT_QUERY =
      "select person.voided, count(person.voided) as count from Person as person "
          + "inner join person.attributes as attribute "
          + "where attribute.voided = 0 and attribute.attributeType.name = :attributeTypeName "
          + "  and person.id in ("
          + "    select p.id from Person as p "
          + "    inner join p.attributes as pa "
          + "    where pa.voided = 0 and pa.attributeType.name='LocationAttribute' "
          + "      and pa.value in (:locations)) "
          + "group by person.voided ";

  private static final String SYNC_TEMPLATES_SQL =
      "select person.uuid as uuid, pi.identifier as identifier, "
          + "person.voided as voided,person.dateChanged as dateModified "
          + "from Person as person "
          + "inner join person.identifiers as pi "
          + "inner join person.attributes as attribute "
          + "where attribute.voided = 0 and attribute.attributeType.name = :attributeTypeName "
          + "and person.id in ("
          + "  select p.id from Person as p "
          + "  inner join p.attributes as pa "
          + "  where pa.voided = 0 and pa.attributeType.name='LocationAttribute' "
          + "    and pa.value in (:locations))";

  private static final String SYNC_IMAGES_SQL = "select person from Person as person  "
      + "inner join person.attributes as attribute "
      + "where attribute.voided = 0 and attribute.attributeType.name = :attributeTypeName "
      + "  and person.id in ("
      + "    select p.id from Person as p "
      + "    inner join p.attributes as pa "
      + "    where pa.voided = 0 and pa.attributeType.name='LocationAttribute' "
      + "      and pa.value in (:locations))";

  private SyncQueryHelper() {

  }

  public static String buildParticipantTemplatesQuery() {
    return SYNC_TEMPLATES_SQL;
  }

  public static String buildParticipantImageQuery() {
    return SYNC_IMAGES_SQL;
  }

  public static String getSyncTotalCountQuery() {
    return SYNC_TOTAL_COUNT_QUERY;
  }

  public static String getSyncIgnoredCountQuery() {
    return SYNC_IGNORED_COUNT_QUERY;
  }

  public static String buildSyncTemplatesQuery() {
    return TEMPLATES_SQL;
  }
}
