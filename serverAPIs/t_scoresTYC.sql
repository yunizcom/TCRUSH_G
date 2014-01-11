-- phpMyAdmin SQL Dump
-- version 4.0.8
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 11, 2014 at 07:48 PM
-- Server version: 5.5.32-cll
-- PHP Version: 5.3.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `yunizcom_mtapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `t_scoresTYC`
--

CREATE TABLE IF NOT EXISTS `t_scoresTYC` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) NOT NULL,
  `uid` varchar(255) NOT NULL,
  `fbid` varchar(255) NOT NULL,
  `levels` varchar(255) NOT NULL,
  `scores` int(255) NOT NULL,
  `tdate` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `nickname` (`nickname`),
  KEY `scores` (`scores`),
  KEY `tdate` (`tdate`),
  KEY `uid` (`uid`),
  KEY `levels` (`levels`),
  KEY `fbid` (`fbid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5574 ;

--
-- Dumping data for table `t_scoresTYC`
--

INSERT INTO `t_scoresTYC` (`id`, `nickname`, `uid`, `fbid`, `levels`, `scores`, `tdate`) VALUES
(5555, 'time born', '1388851238-178121', '', '1', 26, '1388855399'),
(5556, ' gp time born', '1388851238-178121', '', '1', 44, '1388903998'),
(5557, ' gp time born', '1388851238-178121', '', '2', 34, '1388904360'),
(5558, 'New Player', '1388915829-431932', '', '1', 27, '1388918897'),
(5559, 'New Player', '1388918963-694770', '', '1', 14, '1388919599'),
(5560, 'New Player', '1388918963-694770', '', '3', 45, '1388925709'),
(5561, 'New Player', '1388930130-518600', '', '1', 14, '1388930380'),
(5562, 'Alex', '1389010296-474654', '', '1', 21, '1389011522'),
(5563, 'Serene', '1388940388-895708', '', '1', 14, '1389010526'),
(5564, 'cowMoo', '1389010296-474654', '', '2', 46, '1389012593'),
(5565, 'Stanly ^_^', '1389027038-894175', '', '1', 49, '1389027166'),
(5566, 'Mandy', '1389060640-304854', '', '1', 39, '1389060740'),
(5567, 'New Player', '1388940388-895708', '', '2', 62, '1389104539'),
(5568, 'Paul', '1388940388-895708', '', '3', 66, '1389105902'),
(5569, 'Stanly ^_^', '1389027038-894175', '', '2', 70, '1389111850'),
(5570, 'New Player', '1389145400-898224', '', '1', 41, '1389145720'),
(5571, 'New Player', '1389146800-347962', '', '1', 34, '1389146948'),
(5572, 'Dai Ca Thao', '1389146800-347962', '', '2', 32, '1389147898'),
(5573, 'Mama\n', '1389224266-648438', '', '1', 21, '1389224419');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
