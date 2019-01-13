(ns christmas.chr16.day04-test
  (:require [christmas.chr16.day04 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/chr16/day04real"))

(def real-1 "aaaaa-bbb-z-y-x-123[abxyz]")
(def real-2 "a-b-c-d-e-f-g-h-987[abcde]")
(def real-3 "not-a-real-room-404[oarel]")
(def real-4 "totally-real-room-200[decoy]")

(expect 123 (chr/parse-line real-1))
(expect 987 (chr/parse-line real-2))
(expect 404 (chr/parse-line real-3))
(expect 0 (chr/parse-line real-4))

(expect 158835 (chr/day04 file))
(expect 993 (chr/day04b file))

(expect ["veryfencryptedfnamef" 343] (chr/parse-room "qzmt-zixmtkozy-ivhz-343"))

(expect \v (chr/next-char 343 \q))
)