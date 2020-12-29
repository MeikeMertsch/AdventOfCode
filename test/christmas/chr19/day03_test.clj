(ns christmas.chr19.day03-test
  (:require [christmas.chr19.day03 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

(comment
  (def file-a "resources/chr19/day03")
  (def file-b "resources/chr19/day03b")
  (def file-c "resources/chr19/day03c")
  (def realfile "resources/chr19/day03real")

  (expect 6 (chr/day03 file-a))
  (expect 159 (chr/day03 file-b))
  (expect 135 (chr/day03 file-c))
;(expect 1017 (chr/day03 realfile))

  (expect 30 (chr/day03b file-a))
  (expect 610 (chr/day03b file-b))
  (expect 410 (chr/day03b file-c))
;(expect 11432 (chr/day03b realfile))
  )