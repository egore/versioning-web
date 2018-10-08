INSERT INTO role_permissions(role_id, permission)
 VALUES ((SELECT id FROM role),'SHOW_VERSIONS'),
        ((SELECT id FROM role WHERE name = 'Administrators'),'ADMIN_VERSIONS'),
        ((SELECT id FROM role),'SHOW_VCSHOSTS'),
        ((SELECT id FROM role WHERE name = 'Administrators'),'ADMIN_VCSHOSTS'),
        ((SELECT id FROM role),'SHOW_TAGTRANSFORMERS'),
        ((SELECT id FROM role WHERE name = 'Administrators'),'ADMIN_TAGTRANSFORMERS'),
        ((SELECT id FROM role),'SHOW_SERVERS'),
        ((SELECT id FROM role WHERE name = 'Administrators'),'ADMIN_SERVERS'),
        ((SELECT id FROM role),'SHOW_PROJECTS'),
        ((SELECT id FROM role WHERE name = 'Administrators'),'ADMIN_PROJECTS'),
        ((SELECT id FROM role),'SHOW_MAVENREPOSITORIES'),
        ((SELECT id FROM role WHERE name = 'Administrators'),'ADMIN_MAVENREPOSITORIES');