(ns christmas.chr20.day08-test
  (:require [christmas.chr20.day08 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

(comment
  (def realfilename "resources/chr20/day08 real")
(def filename "resources/chr20/day08")
(def testgame {:acc 0, :pos 0, :instr {0 [:nop 0], 1 [:acc 1], 2 [:jmp 4], 3 [:acc 3], 4 [:jmp -3], 5 [:acc -99], 6 [:acc 1], 7 [:jmp -4], 8 [:acc 6]}})

(expect 5 (chr/day08 filename))
(expect 1727 (chr/day08 realfilename))
(expect {:pos 1 :instr {} :acc 11} (chr/acc {:pos 0 :instr {0 [:acc 3]} :acc 8}))
(expect {:pos 6 :instr {} :acc 2} (chr/jmp {:pos 1 :instr {1 [:jmp 5]} :acc 2}))
(expect {:pos 5 :instr {} :acc 4} (chr/nop {:pos 4 :instr {4 [:nop -5]} :acc 4}))
(expect {:pos -1 :instr {} :acc 3} (chr/stp {:pos 7 :instr {} :acc 3}))

(expect 8 (chr/day08b filename))
(expect 552 (chr/day08b realfilename)))
