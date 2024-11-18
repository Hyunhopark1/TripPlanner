--자유 게시판 테이블
CREATE TABLE `board_free_tbl` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                  `content` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                  `createId` bigint NOT NULL,
                                  `createDt` datetime DEFAULT CURRENT_TIMESTAMP,
                                  `viewQty` int NOT NULL DEFAULT '0',
                                  `likeQty` int NOT NULL DEFAULT '0',
                                  `updateDt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                                  `deleteYn` tinyint NOT NULL DEFAULT '0',
                                  PRIMARY KEY (`id`),
                                  KEY `createId` (`createId`),
                                  CONSTRAINT `board_free_tbl_ibfk_1` FOREIGN KEY (`createId`) REFERENCES `user_tbl` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


--유저 테이블
CREATE TABLE `user_tbl` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `loginId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `password` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `gender` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `photo` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `loginId` (`loginId`),
                            UNIQUE KEY `nickname` (`nickname`),
                            UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


--후기 게시판 테이블
CREATE TABLE `board_review_tbl` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `title` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
                                    `content` varchar(3000) COLLATE utf8mb4_general_ci NOT NULL,
                                    `createId` bigint NOT NULL,
                                    `createDt` datetime DEFAULT CURRENT_TIMESTAMP,
                                    `viewQty` int NOT NULL DEFAULT '0',
                                    `likeQty` int NOT NULL DEFAULT '0',
                                    `updateDt` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                    `deleteYn` tinyint NOT NULL DEFAULT '0',
                                    `areaCode` bigint NOT NULL,
                                    PRIMARY KEY (`id`),
                                    KEY `createId` (`createId`),
                                    KEY `board_review_tbl_area_code_tbl_FK` (`areaCode`),
                                    CONSTRAINT `board_review_tbl_area_code_tbl_FK` FOREIGN KEY (`areaCode`) REFERENCES `area_code_tbl` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
                                    CONSTRAINT `board_review_tbl_ibfk_1` FOREIGN KEY (`createId`) REFERENCES `user_tbl` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;